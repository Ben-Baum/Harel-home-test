![Harel Insurance Automation](https://github.com/Ben-Baum/Harel-home-test/actions/workflows/test.yml/badge.svg)

Harel Travel Insurance - Automation Project

OVERVIEW
This project automates the travel insurance purchase flow on the Harel Insurance website. 
It ensures that the core functionality—from continent selection to date calculation—remains functional and accurate.

AUTOMATED TEST SCENARIO
The script performs the following steps automatically:
1. Navigates to the Harel insurance portal.
2. Clicks on "Purchase for the first time".
3. Selects "Europe" as the destination.
4. Selects travel dates:
   - Departure: Today + 7 days.
   - Return: Departure + 29 days (exactly 30 inclusive days).
5. Verifies the "Total Days" calculation in the UI (should display "30 days").
6. Proceeds to the Passenger Details page and verifies it loaded successfully.
7. Waits for 5 seconds (for manual inspection) and closes the browser.

TECHNICAL ARCHITECTURE
- Language: Java 11
- Tools: Selenium WebDriver (Browser control), TestNG (Test runner/Assertions), Maven (Dependency management).
- Methodology: Page Object Model (POM)
  - Every page/screen on the website is represented by a specific Java Class (e.g., DateSelectionPage).
  - This separates the page logic (locators, clicks) from the test flow, making the code maintainable and clean.
- Cloud Readiness: Configured with GitHub Actions for automated CI/CD execution.
- Headless Support: Can run on cloud servers without a monitor using the `-Dselenium.headless=true` flag.

HOW TO RUN
Locally:
Run `mvn clean test` from the root directory.

Cloud (GitHub Actions & Live Report):
Upon pushing to GitHub, the test runs automatically. 
🚀 View the latest Live Test Report here: https://Ben-Baum.github.io/Harel-home-test/index.html
(Note: The link will work after the first successful Cloud run).

