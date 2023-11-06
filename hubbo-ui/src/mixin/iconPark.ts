import {
    Announcement,
    ApiApp,
    AssemblyLine,
    CheckOne,
    ListTwo,
    MonitorCamera,
    Moon,
    PullRequests,
    SunOne,
    Success,
    Telegram,
    CityGate
} from '@icon-park/vue-next'

export default function iconPark(app) {
    app.component('CheckOne', CheckOne)
    app.component('ApiApp', ApiApp)
    app.component('AssemblyLine', AssemblyLine)
    app.component('PullRequests', PullRequests)
    app.component('ListTwo', ListTwo)
    app.component('Announcement', Announcement)
    app.component('MonitorCamera', MonitorCamera)
    app.component('Moon', Moon)
    app.component('SunOne', SunOne)
    app.component('Success', Success)
    app.component('Telegram', Telegram)
    app.component('CityGate', CityGate)
}