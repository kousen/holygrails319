package com.kousenit

class Knight {
    String title
    String name
    Quest quest
    Castle castle

    String toString() { "$title $name" }

    static constraints = {
        title inList: ['Sir', 'King', 'Queen', 'Lord', 'Lady']
        name blank: false
        quest nullable: true
        castle nullable: true
    }
}
