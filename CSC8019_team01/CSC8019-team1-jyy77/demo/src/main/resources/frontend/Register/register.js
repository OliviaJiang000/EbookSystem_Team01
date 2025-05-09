// Registration logic for eBook Library
// Authors: Amin, Amin Grace Brown, and Avin Jayatilake

// -------- Register Button Click Handler --------
// Trigger registration when the register button is clicked
document.getElementById("registerBtn").addEventListener("click", async function(event) {
  event.preventDefault(); // Prevent the default form submission behavior

  // -------- Get Form Input Values --------
  const username = document.getElementById("reg").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
  const confirmPassword = document.getElementById("confirm-password").value.trim();

  // Console log input values for debugging purposes
  console.log("Form Data:", { username, email, password, confirmPassword });

  // -------- Validate Required Fields --------
  if (!username || !email || !password || !confirmPassword) {
    alert("Please fill in all fields.");
    return;
  }

  // -------- Validate Password Confirmation --------
  if (password !== confirmPassword) {
    alert("Passwords do not match.");
    return;
  }

  try {
    // -------- Send Registration Request to Backend --------
    const response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: username,
        email: email,
        password: password
      })
    });

    // -------- Handle Response from Server --------
    if (response.ok) {
      alert("Registration successful! Please check your email.");
      window.location.href = "../Login/login.html"; // Redirect to login page
    } else {
      const data = await response.json();
      alert("Registration failed: " + data.msg); // Show server error message
    }
  } catch (error) {
    // -------- Handle Any Network or Unexpected Errors --------
    console.error("Error:", error);
    alert("An error occurred. Is the backend running?");
  }
});
