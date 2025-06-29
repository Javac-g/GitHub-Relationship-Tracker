# GitHub Follower Tracker

Track your GitHub followers, unfollowers, and non-mutual subscriptions.  
Export results to Excel, view the change history, and track relationships over time.

## 🔍 Features

- ✅ Log in with GitHub OAuth
- 📊 Track followers and following
- 🔁 See who unfollowed you
- 📂 Export to `.xlsx` Excel file
- ⏰ Daily refresh (00:00 and 12:00 UTC)
- ⚡ Instant refresh (Pro feature)
- 🔔 Optional email/Telegram alerts

## 🧪 How It Works

1. Authenticate with your GitHub account
2. App fetches your followers and who you follow
3. Stores the current snapshot and compares it with the previous
4. You get a categorized report:
   - Subscribed on Me (followers)
   - Not Subscribed (you follow them, they don’t follow back)
   - Unsubscribed (they used to follow you but don’t anymore)

## 🚀 Roadmap

- Web dashboard with charts
- GitHub Marketplace listing
- Telegram/Email notifications
- Stripe integration for Pro plan
- Historical graphs

## 📦 Tech Stack

- Java 17 + Spring Boot
- PostgreSQL
- GitHub OAuth + API
- Apache POI (Excel)
- Jackson (JSON)
- React (Frontend, WIP)

## 🛠 Installation (CLI Dev Mode)

```bash
export GITHUB_TOKEN=your_token_here
./gradlew run
