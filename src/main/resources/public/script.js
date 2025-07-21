document.addEventListener('DOMContentLoaded', () => {
    const textInput = document.getElementById('text-input');
    const analyzeBtn = document.getElementById('analyze-btn');
    const loader = document.getElementById('loader');
    const resultsSection = document.getElementById('results-section');
    const errorBox = document.getElementById('error-message');
    const resultSentimentEl = document.getElementById('result-sentiment');

    analyzeBtn.addEventListener('click', async () => {
        const text = textInput.value.trim();
        if (!text) {
            alert('Please enter some text to analyze.');
            return;
        }

        // Reset UI
        resultsSection.style.display = 'none';
        errorBox.style.display = 'none';
        loader.style.display = 'block';
        analyzeBtn.disabled = true;

        try {
            const response = await fetch('/analyze', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ text: text })
            });

            const data = await response.json();
            if (!response.ok) {
                throw new Error(data.error || 'An unknown server error occurred.');
            }

            // Display the result
            resultSentimentEl.textContent = data.sentiment;
            resultSentimentEl.className = 'sentiment-badge ' + data.sentiment;
            resultsSection.style.display = 'block';

        } catch (error) {
            errorBox.textContent = `Error: ${error.message}`;
            errorBox.style.display = 'block';
        } finally {
            loader.style.display = 'none';
            analyzeBtn.disabled = false;
        }
    });
});