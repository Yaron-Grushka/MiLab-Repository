var express = require("express");
const fs = require('fs');
const zlib = require('zlib');
var app = express();
let idIndex = 5;

// Method to show contents of the list to the user:
app.get("/View", (req, res) => {
    let jsonFile = fs.readFileSync('./MyFile.json');
    let file = JSON.parse(jsonFile);
    res.send(file);
})

// Method for adding new items to list:
app.get("/Add", (req, res) => {
    let jsonFile = fs.readFileSync('./MyFile.json');
    let file = JSON.parse(jsonFile);
    let task = req.query.task; // Enter name of new task
    var createId = 0; // This will be the resulting id of the new task
    for (var key in file.todo) { // Traverse each item in the to-do list
        var currentKey = Number(file.todo[key]["id"]); // Fetch the id attribute of each item
        if (currentKey >= createId) {
            createId = currentKey + 1; // The resulting id will be 1 above the currently highest id
        }
    }
    file.todo.push({ id: String(createId), Task: task }); // Enter the new task with its new id to list
    file = JSON.stringify(file, null, 2);
    fs.writeFile('MyFile.json', file, (err) => {
        if (err) {
            console.log(err);
        }
    });
    res.redirect("/View"); // Return to the list view
})

// Method for removing ites from list:
app.get("/Remove", (req, res) => {
    let jsonFile = fs.readFileSync('./MyFile.json');
    let file = JSON.parse(jsonFile);
    let id = req.query.id; // Enter the id of the task to be deleted
    let index = 0; // This variable will tell 
    for (var key in file.todo) { // Traverse each item in the to-do list
        var currentKey = file.todo[key]["id"]; // The id of the item we're currently looking at
        if (currentKey == id) {
            file.todo.splice(index, 1); // Delete the task at the current index
        } else {
            index++; // Increment index
        }
    }
    file = JSON.stringify(file, null, 2);
    fs.writeFile('MyFile.json', file, (err) => {
        if (err) {
            console.log(err);
        }
    });
    res.redirect("/View"); // Return to the list view
})

app.listen(8080, () => {
    console.log("Started listening on 8080");
});
