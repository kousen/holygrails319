package com.kousenit

class ExampleController {
    def geocoderService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render view:'/example/samplemap', model: [
                castleList: Castle.list(params),
                castleCount: Castle.count()]
    }

    def useplugin(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Castle.list(params), model: [castleCount: Castle.count(),
                                             mapColumns : geocoderService.headers(),
                                             mapData    : geocoderService.data()]
    }

    def samplemap() {}
}
