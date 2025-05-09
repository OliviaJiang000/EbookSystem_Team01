// ------------------------------------------------------------------
// reviews.js - Handles review submission and display for a book
// Authors: Amin, Amin Grace Brown, and Avin Jayatilake
// ------------------------------------------------------------------

document.addEventListener('DOMContentLoaded', () => {
    // Get DOM elements
    const submitBtn = document.getElementById('submitBtn');
    const successPage = document.getElementById('successPage');
    const container = document.querySelector('.container');
    const closeBtn = document.getElementById('closeBtn');
    const reviewText = document.getElementById('reviewText');
    const ratingSelect = document.getElementById('rating');
    const reviewsSection = document.querySelector('.reviews-section');

    // Retrieve selected book ID from localStorage
    const bookId = parseInt(localStorage.getItem('reviewBookId'), 10);

    // If no book ID found, redirect to loans page
    if (!bookId || isNaN(bookId)) {
        alert('Book ID is missing. Please go back and select a book to review.');
        window.location.href = '../MyBooks/myBooks.html';  
    }

    // -------------------- Function: Load Reviews from Backend --------------------
    function loadReviews() {
        fetch(`http://localhost:8080/reviews/${bookId}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to load reviews.');
                return response.json();
            })
            .then(data => {
                const reviewContainer = reviewsSection;

                // Clear existing reviews
                const existingReviews = reviewContainer.querySelectorAll('.review');
                existingReviews.forEach(r => r.remove());

                // If no reviews, show default message
                if (data.length === 0) {
                    const msg = document.createElement('div');
                    msg.className = 'review';
                    msg.textContent = 'No reviews yet. Be the first!';
                    reviewContainer.appendChild(msg);
                    return;
                }

                // Add each review to the DOM
                data.forEach(review => {
                    const reviewDiv = document.createElement('div');
                    reviewDiv.className = 'review';
                    reviewDiv.textContent = `"${review.comment}" - ${review.rating}★`;
                    reviewContainer.appendChild(reviewDiv);
                });
            })
            .catch(error => {
                console.error('Error loading reviews:', error);
            });
    }

    // -------------------- Submit Review --------------------
    submitBtn.addEventListener('click', () => {
        const comment = reviewText.value.trim();
        const rating = parseInt(ratingSelect.value);
        const token = localStorage.getItem('token');

        // Validate input
        if (!comment || isNaN(rating)) {
            alert('Please write a review and select a rating.');
            return;
        }

        const reviewData = {
            bookId: parseInt(bookId),
            rating: rating,
            comment: comment
        };

        // Send POST request to backend
        fetch('http://localhost:8080/reviews/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(reviewData)
        })
        .then(response => {
            if (!response.ok) throw new Error('Failed to submit review.');
            return response.text();
        })
        .then(msg => {
            console.log('Success:', msg);
            container.style.display = 'none';              // Hide form
            successPage.classList.remove('hidden');       // Show success message
            loadReviews();                                // Reload reviews
        })
        .catch(error => {
            console.error('Error submitting review:', error);
            alert('Error submitting review. Please try again later.');
        });
    });

    // -------------------- Close Button Handler --------------------
    closeBtn?.addEventListener('click', () => {
        window.location.href = '../MyBooks/myBooks.html'; 
    });

    // -------------------- Initial Load --------------------
    loadReviews();
});
