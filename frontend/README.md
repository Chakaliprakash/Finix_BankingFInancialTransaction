# Finix Frontend

Responsive React + Vite dashboard for the Finix Banking & Financial Transaction project.

## Run locally

```bash
cd frontend
npm install
npm run dev
```

Open the local URL printed by Vite (normally http://localhost:5173).

## Build

```bash
npm run build
npm run preview
```

## Current functionality

- Responsive banking dashboard layout
- Balance visibility toggle
- Account overview cards
- Monthly balance and spending visualizations
- Transaction search and category filtering
- Send-money modal with form validation
- Demo transfer updates the activity table in local React state

## Backend integration status

This frontend currently runs with demonstration data. The repository's current backend is a plain Java console application and does not expose HTTP/REST endpoints yet. The UI therefore does not claim to execute real banking transactions.

When REST endpoints are added, connect the screens to the backend and replace the local demo state with API responses. Keep all balance changes and transaction validation on the server; never trust client-side amounts as authoritative.

## Stack

- React 18
- Vite 6
- Lucide React
- CSS (responsive layout)