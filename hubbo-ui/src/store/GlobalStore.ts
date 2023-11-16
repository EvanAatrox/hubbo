import {defineStore} from "pinia";
import {ref} from "vue";

export const useGlobalStore = defineStore('global', () => {
    const appName = ref("hubbo")

    return {
        appName
    }
})
