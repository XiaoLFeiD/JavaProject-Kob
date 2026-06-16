import { createRouter, createWebHistory } from 'vue-router'
import NotFoundView from "../views/error/NotFoundView"
import PkIndexView from "../views/pk/PkIndexView"
import RankListIndexView from "../views/ranklist/RankListIndexView"
import RecordIndexView from "../views/record/RecordIndexView"
import UserBotIndexView from "../views/user/bot/UserBotIndexView"
import UserAccountLogin from "../views/user/account/UserAccountLogin"
import UserAccountRegister from "../views/user/account/UserAccountRegister"
import HomeView from "../views/home/HomeView"
import store from '../store/index'
import RecordContentView from "../views/record/RecordContentView"

//路由
const routes = [{

        path: "/",
        name: "home",
        component: HomeView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/user/account/login/",
        name: "user_account_login",
        component: UserAccountLogin,
        meta: {
            requestAuth: false,
        }
    },
    {
        path: "/user/account/register/",
        name: "user_account_register",
        component: UserAccountRegister,
        meta: {
            requestAuth: false,
        }
    },
    {
        path: "/pk/",
        name: "pk_index",
        component: PkIndexView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/record/:recordId/",
        name: "record_content",
        component: RecordContentView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/ranklist/",
        name: "ranklist_index",
        component: RankListIndexView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/record/",
        name: "record_index",
        component: RecordIndexView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/user/bot/",
        name: "userbot_index",
        component: UserBotIndexView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/404/",
        name: "404",
        component: NotFoundView,
        meta: {
            requestAuth: false,
        }
    },
    {
        path: "/:catchAll(.*)",
        redirect: "/404/"
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    let jwt_token = localStorage.getItem("jwt_token");
    if (jwt_token) {
        store.commit("updateToken", jwt_token);
        store.dispatch("getinfo", {
            success() {
                next();
            },
            error() {
                alert("token无效，请重新登录！");
                router.push({ name: "user_account_login" });
            }
        })
    } else {
        if (to.meta.requestAuth && !store.state.user.is_login) {
            next({ name: "user_account_login" });

        } else {
            next();
        }
    }
})


export default router