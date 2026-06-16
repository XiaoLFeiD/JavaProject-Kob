import { createStore } from 'vuex'
import ModuleUser from "../store/user"
import ModulePk from "../store/pk"
import ModuleRecord from "../store/record"
//全局变量的存储的模块
export default createStore({
    state: {},
    getters: {},
    mutations: {},
    actions: {},
    modules: {
        user: ModuleUser,
        pk: ModulePk,
        record: ModuleRecord,
    }
})