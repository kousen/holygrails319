package com.kousenit

class Quest {
    String name

    String toString() { name }

    static hasMany = [tasks: Task, knights: Knight]

    static constraints = {
        name blank: false
    }
}
