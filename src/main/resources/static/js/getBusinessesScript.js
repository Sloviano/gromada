
// Wait until the DOM is fully loaded
// document.addEventListener('DOMContentLoaded', function() {
//     // Fetch the list of objects from the API endpoint
//     fetch('/api/objects')
//         // Check for HTTP errors and parse the response as JSON
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok');
//             }
//             return response.json();
//         })
//         // Process the JSON data
//         .then(data => {
//             const list = document.getElementById('objectList');
//             // Iterate over each object in the received list
//             data.forEach(obj => {
//                 // Create a list item for each object
//                 const li = document.createElement('li');
//                 // Display relevant object properties
//                 li.textContent = `ID: ${obj.id}, Name: ${obj.name}`;
//                 list.appendChild(li);
//             });
//         })
//         // Handle any errors during fetch or processing
//         .catch(error => console.error('Error fetching objects:', error));
// });
