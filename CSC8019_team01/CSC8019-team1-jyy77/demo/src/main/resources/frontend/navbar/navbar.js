/**
 * auth-status.js - eBook Library
 * Description: Checks login status, updates UI visibility, and handles logout logic.
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

document.addEventListener("DOMContentLoaded", function () {
  // -------- Check Login Status on Page Load --------
  const isLoggedIn = localStorage.getItem("isLoggedIn"); // Boolean flag from successful login

  const loginBtn = document.querySelector(".btn-login");
  const signupBtn = document.querySelector(".btn-signup");
  const settingsDropdown = document.getElementById("settingsDropdown");

  if (isLoggedIn) {
    // If user is logged in: show logout and settings, hide login
    if (loginBtn) loginBtn.style.display = "none";
    if (signupBtn) signupBtn.style.display = "inline-block";  // Used here as "Sign Out"
    if (settingsDropdown) settingsDropdown.style.display = "inline-block";
  } else {
    // If not logged in: show login button, hide sign out and settings
    if (loginBtn) loginBtn.style.display = "inline-block";
    if (signupBtn) signupBtn.style.display = "none";
    if (settingsDropdown) settingsDropdown.style.display = "none";
  }
});

// -------- Handle Logout (Clear Local Storage & Redirect) --------
document.addEventListener("click", function (e) {
  if (e.target && e.target.classList.contains("btn-signup")) {
    localStorage.removeItem("isLoggedIn"); // Clear login flag
    localStorage.removeItem("token");      // Clear authentication token
    alert("Logged out");
    window.location.href = "../Login/login.html"; // Redirect to login page
  }
});
