package com.kousenit

import groovy.transform.ToString

@ToString(includeNames = true, includes = ['name','priority'])
class Task {
    String name
    int priority = 3
    Date startDate = new Date()
    Date endDate = new Date()
    boolean completed

    static belongsTo = [quest: Quest]

    static constraints = {
        name blank: false
        priority range: 1..5
        endDate validator: { val, task ->
            val >= task.startDate
        }
    }
}
