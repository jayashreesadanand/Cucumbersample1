'use strict';

const express = require('express');
const nunjucks = require('nunjucks');
const bodyParser = require('body-parser');
const session = require('express-session');
const path = require('path');

const registrationRoutes = require('./routes/registration');
const formsRoutes = require('./routes/forms');

const app = express();
const PORT = process.env.PORT || 3000;
const isProduction = process.env.NODE_ENV === 'production';

// Configure Nunjucks templating with GOV.UK Frontend macros
nunjucks.configure(['views', 'node_modules/govuk-frontend/govuk'], {
  autoescape: true,
  express: app
});
app.set('view engine', 'html');

// Serve GOV.UK Frontend static assets
app.use('/assets', express.static(path.join(__dirname, 'node_modules/govuk-frontend/govuk/assets')));
app.use('/stylesheets', express.static(path.join(__dirname, 'public/stylesheets')));

// Parse form bodies
app.use(bodyParser.urlencoded({ extended: true }));

// Session middleware – cookie is secure in production (HTTPS only)
app.use(session({
  secret: process.env.SESSION_SECRET || 'govuk-webapp-secret',
  resave: false,
  saveUninitialized: false,
  cookie: {
    secure: isProduction,
    httpOnly: true,
    sameSite: 'lax'
  }
}));

// CSRF protection using csrf-csrf – attach token to every response local for use in templates
const { doubleCsrf } = require('csrf-csrf');
const { doubleCsrfProtection, generateToken } = doubleCsrf({
  getSecret: () => process.env.CSRF_SECRET || process.env.SESSION_SECRET || 'govuk-webapp-csrf-secret',
  cookieName: 'x-csrf-token',
  cookieOptions: { secure: isProduction, httpOnly: true, sameSite: 'lax' }
});
app.use(doubleCsrfProtection);
app.use((req, res, next) => {
  res.locals.csrfToken = generateToken(req, res);
  next();
});

// Routes
app.use('/register', registrationRoutes);
app.use('/forms', formsRoutes);

// Home page
app.get('/', (req, res) => {
  res.render('home.html', { serviceName: 'GOV.UK Service' });
});

app.listen(PORT, () => {
  console.log(`GOV.UK web app listening on port ${PORT}`);
});

module.exports = app;
