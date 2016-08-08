import com.kousenit.Castle
import com.kousenit.Quest

class BootStrap {
    def geocoderService

    def init = { servletContext ->
        Quest q = new Quest(name: 'Seek the grail')
                .addToTasks(name: 'Run away from killer rabbit', priority: 2)
                .addToTasks(name: 'Defeat the Black Knight', completed: true)
                .save(failOnError: true)
        Castle camelot = new Castle(name: 'Camelot', city: 'Hartford', state: 'CT')
                .addToKnights(title: 'Sir', name: 'Lancelot', quest: q)
                .addToKnights(title: 'King', name: 'Arthur', quest: q)
                .addToKnights(title: 'Sir', name: 'Robin', quest: q)
        geocoderService.fillInLatLng(camelot)
                       .save(failOnError: true)

        Castle swamp = new Castle(name: 'Swamp', city: 'New York', state: 'NY')
        geocoderService.fillInLatLng(swamp)
                       .save(failOnError: true)

        Castle fenway = new Castle(name: 'Fenway', city: 'Boston', state: 'MA')
        geocoderService.fillInLatLng(fenway)
                        .save(failOnError: true)
    }

    def destroy = {
    }
}
