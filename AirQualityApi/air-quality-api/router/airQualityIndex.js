const express = require('express');
const router = express.Router();
const axios = require('axios')


router.get('/:cityName', (req, res, next) => {
    console.log("girdi")
        const cityName = req.params.cityName;
        axios.get('http://api.waqi.info/feed/' + cityName + '/?token=' + process.env.AQI_TOKEN).then(result => {
            console.log("hava kalite indexi"+         result.data.data.aqi)
            res.status(200).json({
                result : result.data.data
            })
        }).catch(err => {
            res.status(500).json({
                Error: err
            })
        })
});



module.exports = router;

