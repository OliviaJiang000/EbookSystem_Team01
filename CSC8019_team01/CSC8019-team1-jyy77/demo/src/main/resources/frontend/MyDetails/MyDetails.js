/**
 * MyDetails.js - eBook Library
 * Handles user data retrieval and email update functionality.
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

document.addEventListener("DOMContentLoaded", function () {

  // -------------------- Dropdown Hover Handling --------------------
  // Show/hide dropdown menu on hover
  const dropdowns = document.querySelectorAll(".dropdown");

  dropdowns.forEach((dropdown) => {
    const toggle = dropdown.querySelector(".dropdown-toggle");
    const menu = dropdown.querySelector(".dropdown-menu");
    let hideTimeout;

    if (toggle && menu) {
      dropdown.addEventListener("mouseenter", () => {
        clearTimeout(hideTimeout);
        menu.classList.add("show");
      });

      dropdown.addEventListener("mouseleave", () => {
        hideTimeout = setTimeout(() => {
          menu.classList.remove("show");
        }, 300);
      });
    }
  });

  // -------------------- Fetch Logged-In User's Info --------------------
  // Fetches the user's username and email from the backend
  async function fetchUserDetails() {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        alert('You are not authenticated.');
        return;
      }

      const response = await fetch('http://localhost:8080/api/auth/me', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch user data');
      }

      const data = await response.json();
      console.log(data);

      // Populate DOM elements with current user info
      document.getElementById('current-username').textContent = data.username;
      document.getElementById('current-email').textContent = data.email;
    } catch (error) {
      console.error('Error fetching user data:', error);
      alert('There was an error retrieving your data.');
    }
  }

  // -------------------- Email Update Handler --------------------
  // Submits a new email address to the backend for update
  const form = document.getElementById('update-details-form');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const token = localStorage.getItem('token');
    const newEmail = document.getElementById('new-email').value;

    try {
      const response = await fetch('http://localhost:8080/api/auth/email', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({ newEmail })
      });

      if (response.ok) {
        alert('Email updated successfully!');
        document.getElementById('current-email').textContent = newEmail;
        document.getElementById('new-email').value = ''; // Clear form
      } else {
        const errorData = await response.json();
        console.error('Error response:', errorData);
        alert('Failed to update email: ' + (errorData.message || 'Unknown error.'));
      }
    } catch (err) {
      console.error('Request failed:', err);
      alert('Network error while updating email.');
    }
  });

  // -------------------- Initialize --------------------
  // Load user data when page loads
  window.onload = fetchUserDetails;
});
