package com.hppk.toctw.data

import java.lang.Exception

class UserNotExistException(id: String): Exception("$id is not exist")

class BoothNotExistException(id: String): Exception("$id is not exist")