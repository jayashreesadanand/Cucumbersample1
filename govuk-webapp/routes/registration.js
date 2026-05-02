'use strict';

const express = require('express');
const { body, validationResult } = require('express-validator');
const NotifyClient = require('notifications-node-client').NotifyClient;

const router = express.Router();

const NOTIFY_API_KEY = process.env.NOTIFY_API_KEY || '';
const REGISTRATION_EMAIL_TEMPLATE_ID = process.env.REGISTRATION_EMAIL_TEMPLATE_ID || '';

// GET /register – show the registration form
router.get('/', (req, res) => {
  res.render('registration.html', {
    serviceName: 'GOV.UK Service',
    errors: [],
    values: {}
  });
});

// POST /register – validate and process the registration form
router.post('/',
  [
    body('fullName')
      .trim()
      .notEmpty().withMessage('Enter your full name'),
    body('email')
      .trim()
      .isEmail().withMessage('Enter a valid email address'),
    body('password')
      .isLength({ min: 8 }).withMessage('Password must be at least 8 characters')
  ],
  async (req, res) => {
    const errors = validationResult(req);

    if (!errors.isEmpty()) {
      const errorList = errors.array().map(e => ({
        text: e.msg,
        href: `#${e.path}`
      }));
      return res.render('registration.html', {
        serviceName: 'GOV.UK Service',
        errors: errorList,
        values: req.body
      });
    }

    const { fullName, email } = req.body;

    // Send registration confirmation via GOV.UK Notify
    if (NOTIFY_API_KEY && REGISTRATION_EMAIL_TEMPLATE_ID) {
      try {
        const notifyClient = new NotifyClient(NOTIFY_API_KEY);
        await notifyClient.sendEmail(REGISTRATION_EMAIL_TEMPLATE_ID, email, {
          personalisation: {
            name: fullName,
            verification_link: `${process.env.BASE_URL || 'http://localhost:3000'}/register/verify`
          }
        });
      } catch (notifyError) {
        console.error('GOV.UK Notify error:', notifyError.message);
      }
    }

    // Store the registered name in session for the confirmation page
    req.session.registeredName = fullName;
    res.redirect('/register/confirmation');
  }
);

// GET /register/confirmation – show confirmation page after registration
router.get('/confirmation', (req, res) => {
  const registeredName = req.session.registeredName || 'User';
  res.render('registration-confirmation.html', {
    serviceName: 'GOV.UK Service',
    name: registeredName
  });
});

module.exports = router;
