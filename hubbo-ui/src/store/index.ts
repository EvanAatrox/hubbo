import {useGlobalStore} from "@/store/GlobalStore.ts";
import {useUserStore} from "@/store/UserStore.ts"


interface AppStore {
    globalStore: ReturnType<typeof useGlobalStore>
    userStore: ReturnType<typeof useUserStore>
}

const appStore: AppStore = {} as AppStore;


export const initialStore = () => {
    appStore.globalStore = useGlobalStore()
    appStore.userStore = useUserStore()
}

export default appStore;
export type {AppStore};

