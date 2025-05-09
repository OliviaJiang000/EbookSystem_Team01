/**
 * Login & Security Script
 * Handles password update and dropdown interactivity.
 *
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

document.addEventListener("DOMContentLoaded", function () {

  // ===== Dropdown Hover Handling =====
  const dropdowns = document.querySelectorAll(".dropdown");

  dropdowns.forEach((dropdown) => {
    const toggle = dropdown.querySelector(".dropdown-toggle");
    const menu = dropdown.querySelector(".dropdown-menu");
    let hideTimeout;

    if (toggle && menu) {
      // Show dropdown menu on hover
      dropdown.addEventListener("mouseenter", () => {
        clearTimeout(hideTimeout);
        menu.classList.add("show");
      });

      // Hide dropdown menu after delay when mouse leaves
      dropdown.addEventListener("mouseleave", () => {
        hideTimeout = setTimeout(() => {
          menu.classList.remove("show");
        }, 300);
      });
    }
  });

  // ===== Password Update Functionality =====
  document.querySelector('.button-update').addEventListener('click', (e) => {
    e.preventDefault(); // Prevent default form submission

    // Get input values
    const current = document.getElementById('current-password').value.trim();
    const newPwd = document.getElementById('new-password').value.trim();
    const retype = document.getElementById('retype-password').value.trim();

    // Validate required fields
    if (!current || !newPwd || !retype) {
      alert("All fields are required.");
      return;
    }

    // Validate password match
    if (newPwd !== retype) {
      alert("New passwords do not match.");
      return;
    }

    // Get token for authentication
    const token = localStorage.getItem("token");
    if (!token) {
      alert("Please log in again.");
      window.location.href = "../Login/login.html";
      return;
    }

    // Send PUT request to update password
    fetch("http://localhost:8080/api/auth/update", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
      },
      body: JSON.stringify({
        currentPassword: current,
        newPassword: newPwd
      })
    })
    .then(res => {
      if (res.ok) {
        alert("Password updated successfully. Please log in again.");
        localStorage.removeItem("token"); // Clear token
        window.location.href = "../Login/login.html"; // Redirect to login
      } else {
        return res.text().then(msg => {
          alert("Failed to update password: " + msg);
        });
      }
    })
    .catch(err => {
      alert("Error: " + err.message);
    });
  });

  // ===== Optional: Reset Button Functionality =====
  document.querySelector('.button-reset')?.addEventListener('click', () => {
    document.getElementById('current-password').value = '';
    document.getElementById('new-password').value = '';
    document.getElementById('retype-password').value = '';
  });

});
