package com.kousenit

import grails.transaction.Transactional

@Transactional
class GeocoderService {
    public static final String BASE = 'http://maps.googleapis.com/maps/api/geocode/xml?'

    def fillInLatLng(Castle c) {
        def encoded = [c.city, c.state].collect {
            URLEncoder.encode(it, 'UTF-8')
        }.join(',')
        String qs = "address=$encoded"
        def root = new XmlSlurper().parse("$BASE$qs")
        def loc = root.result[0].geometry.location
        c.latitude = loc.lat.toDouble()
        c.longitude = loc.lng.toDouble()
        return c
    }

    def headers() {
        [['number','Lat'],['number','Lng'],['string','Name']]
    }

    def data() {
        Castle.list().collect { c ->
            [c.latitude, c.longitude, c.toString()]
        }
    }
}
