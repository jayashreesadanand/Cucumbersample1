'use strict';

const express = require('express');
const router = express.Router();

// GET /forms – landing page linking to GOV.UK Forms
router.get('/', (req, res) => {
  res.render('forms.html', {
    serviceName: 'GOV.UK Service',
    formsUrl: process.env.GOVUK_FORMS_URL || 'https://forms.service.gov.uk'
  });
});

module.exports = router;
