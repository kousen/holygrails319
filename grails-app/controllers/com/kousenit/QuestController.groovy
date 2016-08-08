package com.kousenit

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class QuestController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

//    def index(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        respond Quest.list(params), model:[questCount: Quest.count()]
//    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [questList: Quest.list(params), questCount: Quest.count()]
    }

//    def show(Quest quest) {
//        respond quest
//    }

    def show(Quest quest) {
        // lookup quest by request id, then...
        if(quest == null) {
            render status:404
        }
        else {
            return [quest: quest]
        }
    }

    def create() {
        respond new Quest(params)
    }

    @Transactional
    def save(Quest quest) {
        if (quest == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (quest.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond quest.errors, view:'create'
            return
        }

        quest.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'quest.label', default: 'Quest'), quest.id])
                redirect quest
            }
            '*' { respond quest, [status: CREATED] }
        }
    }

    def edit(Quest quest) {
        respond quest
    }

    @Transactional
    def update(Quest quest) {
        if (quest == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (quest.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond quest.errors, view:'edit'
            return
        }

        quest.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'quest.label', default: 'Quest'), quest.id])
                redirect quest
            }
            '*'{ respond quest, [status: OK] }
        }
    }

    @Transactional
    def delete(Quest quest) {

        if (quest == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        quest.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'quest.label', default: 'Quest'), quest.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'quest.label', default: 'Quest'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
