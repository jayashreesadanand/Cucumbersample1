# GOV.UK Webapp

A Node.js/Express web application built with GOV.UK Frontend design system, integrating GOV.UK Notify for user registration emails.

## Prerequisites

- Node.js 18+
- A [GOV.UK Notify](https://www.notifications.service.gov.uk) account with an API key and email template

## Setup

```bash
cd govuk-webapp
npm install
npm run build:css
```

## Environment Variables

Create a `.env` file (or set environment variables):

| Variable | Description |
|---|---|
| `PORT` | HTTP port (default: `3000`) |
| `SESSION_SECRET` | Express session secret |
| `NOTIFY_API_KEY` | GOV.UK Notify API key |
| `REGISTRATION_EMAIL_TEMPLATE_ID` | Notify template ID for registration emails |
| `BASE_URL` | Public base URL (e.g. `https://your-service.gov.uk`) |
| `GOVUK_FORMS_URL` | URL of your GOV.UK Forms instance (default: `https://forms.service.gov.uk`) |

## GOV.UK Notify Email Template

In your Notify dashboard, create an email template with the following personalisation fields:

- `((name))` — the user's full name
- `((verification_link))` — the email verification link

## Running

```bash
npm start
```

The app will be available at `http://localhost:3000`.

## Routes

| Route | Description |
|---|---|
| `GET /` | Home page |
| `GET /register` | Registration form |
| `POST /register` | Submit registration (triggers Notify email) |
| `GET /register/confirmation` | Registration success page |
| `GET /forms` | GOV.UK Forms access page |
