const AlphaVantageAPI = require('alpha-vantage-cli').AlphaVantageAPI;
const express = require('express');
const request = require('request');
const bodyParser = require('body-parser');
const FCM = require('fcm-push');
const serviceKey = 'AAAA0LmF77M:APA91bH0jtYlutZ4YI_gKQ8ABeHI9FMHG0S_WvEzffDs2ajOWEOoQ-39AfPf1HJ9bOAJKmeZklOaX0JZmeWBEl57kNWKoa1LSlt7Lh9bJ9UmcxsHo8LSRfLOhfOyxgz0R9v_CshoSuNj';

let fcm = new FCM(serviceKey);
let app = express();
app.use(bodyParser.json());

let apiKey = 'MIZRFKDDMFHXAZHO'; // For Alpha Vantage
let tokens = {}; // To keep device tokens
let isSending = false; // This variable determines whether to loop on sending updates on a certain stock
let interval; // This variable will be used for the setInterval method

// Post request for sending token to server:
app.post('/:user/token', (req, res, next) => {
	// Get token from user:
    let token = req.body.token;
	if (!token) return res.status(400).json({err: "missing token"});
    console.log(`Received save token request from ${req.params.user} for token=${token}`);
	
	// Put it as a value in the JSON object 'tokens' with the user's username as key:
    tokens[req.params.user] = token;
    res.status(200).json({msg: "saved ok"});
});

// Post request for fetching stock info:
app.post('/:user/stock', (req, res, next) => {
	// Reset interval (stop sending info for previous stock):
	if(isSending){ // True only if a request has been sent previously
		isSending = false;
		clearInterval(interval);
	}
	
	// Get relevant parameters from client request:
	let symbol = req.body.stock;
	if (!symbol) return res.status(400).json({err: "missing symbol"});
	let targetToken = tokens[req.params.user];
	if (!targetToken) return res.status(400).json({err: "missing user"});
	
	// Send request:
	isSending = true;
	sendStockInfo(symbol, targetToken);
	interval = setInterval(() => sendStockInfo(symbol, targetToken), 15000);
	return res.status(200).json({msg: "msg sent successfully"});
});

// Function for fetching info from Alpha Vantage and sending to the user via Firebase
function sendStockInfo(symbol, targetToken){
	// URL for sending request to Alpha Vantage:
	let url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${symbol}&apikey=${apiKey}`;
	request(url,(err, response, body) => {
		if(err) {
			console.log('error:', error);
            return res.error(err);
         } else {
			let stockInfo = JSON.parse(body);
			// Didn't receive the response we were expecting:
			if (!stockInfo || !stockInfo["Global Quote"]){
				console.log('error with response to request!! ' + symbol);
				fcm.send({
					to: targetToken,
					notification: {
						title: "ERROR:",
						body: "ERROR"
					}
				}, (err, response) => {
					if (err) console.log('error sending message!!' + targetToken);
				});
				return;
			}
			// Extract the relevant data from the response body and send it to the user via FCM:
			let value = stockInfo["Global Quote"]["05. price"];
			let name = stockInfo["Global Quote"]["01. symbol"];
			fcm.send({
				to: targetToken,
				notification: {
					title: "Value:",
					body: symbol + " " + value
				}
			}, (err, response) => {
				if (err) console.log('error sending message!!' + targetToken);
			});
		}			
	});
	return;
}

app.listen(8080, () => {
    console.log("Started listening on 8080");
});
	