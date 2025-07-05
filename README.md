# Temple PDF Sender — Version 1

Automates sending a daily PDF from the temple’s WhatsApp account to a list of
contacts maintained in an Excel file.

---

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


