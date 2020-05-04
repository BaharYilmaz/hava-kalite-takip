const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken')
const User = require('../model/user');
const UserController = require('../controller/user')


router.post('/signup',UserController.users_userSignUp)
router.post('/login',UserController.users_userLogin)
router.delete("/:userId",UserController.users_removeUser)
module.exports = router;