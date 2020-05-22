const express = require('express');
const router = express.Router();
const axios = require('axios')
const ComplaintController = require('../controller/complaint')



router.get('/:cityName',ComplaintController.airquality_get);



module.exports = router;

