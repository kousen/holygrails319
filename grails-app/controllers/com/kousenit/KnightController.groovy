package com.kousenit

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class KnightController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Knight.list(params), model:[knightCount: Knight.count()]
    }

    def show(Knight knight) {
        respond knight
    }

    def create() {
        respond new Knight(params)
    }

    @Transactional
    def save(Knight knight) {
        if (knight == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (knight.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond knight.errors, view:'create'
            return
        }

        knight.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'knight.label', default: 'Knight'), knight.id])
                redirect knight
            }
            '*' { respond knight, [status: CREATED] }
        }
    }

    def edit(Knight knight) {
        respond knight
    }

    @Transactional
    def update(Knight knight) {
        if (knight == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (knight.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond knight.errors, view:'edit'
            return
        }

        knight.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'knight.label', default: 'Knight'), knight.id])
                redirect knight
            }
            '*'{ respond knight, [status: OK] }
        }
    }

    @Transactional
    def delete(Knight knight) {

        if (knight == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        knight.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'knight.label', default: 'Knight'), knight.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'knight.label', default: 'Knight'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
