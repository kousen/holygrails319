package com.kousenit

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TaskController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Task.list(params), model:[taskCount: Task.count()]
    }

    def show(Task task) {
        respond task
    }

    def create() {
        respond new Task(params)
    }

    @Transactional
    def save(Task task) {
        if (task == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (task.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond task.errors, view:'create'
            return
        }

        task.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect task
            }
            '*' { respond task, [status: CREATED] }
        }
    }

    def edit(Task task) {
        respond task
    }

    @Transactional
    def update(Task task) {
        if (task == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (task.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond task.errors, view:'edit'
            return
        }

        task.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect task
            }
            '*'{ respond task, [status: OK] }
        }
    }

    @Transactional
    def delete(Task task) {

        if (task == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        task.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
