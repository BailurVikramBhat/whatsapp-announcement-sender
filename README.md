# WhatsApp-Announcement-Sender

Automates sending a daily (or on-demand) PDF from the temple’s WhatsApp
account to contacts listed in an Excel file.  
Built in Java 17, driven by Selenium 4, configured with properties files,
guarded by GitHub Actions CI + branch-protection.

---

## ✨ Key Features (v2)

| Category | Details |  
|----------|---------|  
| **Excel analytics** | Counts total rows, valid / missing / invalid phones, missing names. |  
| **Rollover** | Adds a `SentOn` column; rows stamped with today’s date are skipped on future runs. |  
| **Error visibility** | *(optional)* creates an **“Errors”** sheet listing skipped rows + reason. |  
| **Headless, sandbox-safe Chrome** | Unique `--user-data-dir` per run avoids profile clashes in CI. |  
| **Randomised delay** | 18-25 s (configurable) to simulate human sending and avoid spam flags. |  
| **Max daily cap** | `max.contacts` throttles how many unsent rows are processed per run. |  
| **Selectors externalised** | All CSS / XPath live in `locators.properties`. |  
| **Unit & integration tests** | JUnit 5 + Mockito; workbook and driver mocked for millisecond runs. |  
| **CI / CD** | GitHub Actions builds every PR / push, releases on Git tag, blocks merge until green. |  
| **Logging** | Logback → console + daily rolling `logs/temple-sender-YYYY-MM-DD.log`. |  

## ✨  Features (v1)

- **Selenium 4** WebDriver automation on WhatsApp Web  
- **WebDriverManager** auto-downloads the matching ChromeDriver  
- Reads contacts from **`contacts.xlsx`** via Apache POI  
- Randomised delay (18-25 s) between sends to mimic human use  
- Max-send throttle via `max.contacts` property  
- **Selectors externalised** in `locators.properties` (simple POM, no PageFactory)  
- Logback logging → console **and** daily rolling `logs/` files  
- Unit-test suite (JUnit 5 + Mockito) covering core utilities  
- Sample test data  
  - `contacts.xlsx` (2 demo rows)  
  - `temple_notice.pdf` (dummy “Testing message”)  

---


---

## ⚙️  Configuration (`application.properties`)

| Key                   | Purpose                                   | Example                   |  
|-----------------------|-------------------------------------------|---------------------------|  
| `excel.file.path`     | XLSX with Name (A), Phone (B)             | `src/main/resources/contacts.xlsx` |  
| `pdf.file.path`       | Absolute/relative path to the PDF         | `/home/user/notice.pdf`   |  
| `max.contacts`        | Daily send cap                            | `150`                     |  
| `delay.min.millis`    | Random delay lower bound (ms)             | `18000`                   |  
| `delay.max.millis`    | Random delay upper bound (ms)             | `25000`                   |  
| `log.level`           | Logback root level (`INFO`/`DEBUG` …)     | `INFO`                    |  

---

## 🚀  Build & Run

```bash
git clone <repo>
cd whatsapp-announcement-sender

# compile + run tests
mvn clean test

# package
mvn clean package

# execute
java -jar target/whatsapp-announcement-sender-1.0.0.jar


