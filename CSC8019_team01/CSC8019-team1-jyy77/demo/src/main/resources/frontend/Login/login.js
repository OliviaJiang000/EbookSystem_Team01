/**
 * Login Script
 * Handles login form submission, authentication via API,
 * stores JWT token, and redirects user based on role (ADMIN or USER).
 *
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

// Listen for login form submission
document.getElementById('login-form').addEventListener('submit', function (e) {
    e.preventDefault(); // Prevent default form reload

    // Get input values
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    // Basic validation
    if (!username || !password) {
        alert("Please enter both username and password.");
        return;
    }

    // Send login request to backend
    fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(async response => {
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Login failed");
        }
        return response.json();
    })
    .then(data => {
        // Ensure token exists
        if (!data.token) throw new Error("Please register");

        // Store JWT token in localStorage
        localStorage.setItem("token", data.token);

        // Fetch user info to determine role
        return fetch("http://localhost:8080/api/auth/me", {
            headers: {
                "Authorization": "Bearer " + data.token
            }
        });
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to fetch user info");
        return res.json();
    })
    .then(user => {
        if (!user.role) throw new Error("Role not found in user data");

        // Redirect based on user role
        if (user.role === "ADMIN") {
            window.location.href = "../Admin/admin.html";
        } else {
            window.location.href = "../HomePage/HomePage/HomePage.html";
        }
    })
    .catch(error => {
        alert("Login failed: " + error.message);
    });
});
