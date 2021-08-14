import { createRouter, createWebHistory } from "vue-router"
import { moveRoom } from "@/lib/websocket"

import LandingLayout from "@/layouts/LandingLayout.vue"
import OfficeLayout from "@/layouts/OfficeLayout.vue"
import RoomLayout from "@/layouts/RoomLayout.vue"
import AuthLayout from "@/layouts/AuthLayout.vue"

import Home from "@/views/Home.vue"
import RegistOffice from "@/views/RegistOffice.vue"
import Office from "@/views/Office.vue"
import Members from "@/views/Members.vue"
import Admin from "@/views/Admin.vue"
import Room from "@/views/Room.vue"
import ResetPassword from "@/views/ResetPassword.vue"
import ChangePassword from "@/views/ChangePassword.vue"
import Signup from "@/views/Signup.vue"
import store from "@/store"

const routes = [
  {
    path: "/",
    component: LandingLayout,
    children: [
      {
        path: "",
        name: "Home",
        component: Home,
      },
      {
        path: "regist-office",
        name: "RegistOffice",
        component: RegistOffice,
      },
    ],
  },
  {
    path: "/office",
    component: OfficeLayout,
    meta: { loginRequired: true },
    children: [
      {
        path: "",
        name: "Office",
        component: Office,
      },
      {
        path: "members",
        name: "Members",
        component: Members,
      },
      {
        path: "admin",
        name: "Admin",
        component: Admin,
      },
    ],
  },
  {
    path: "/rooms",
    component: RoomLayout,
    meta: { loginRequired: true },
    beforeEnter: (to, from, next) => {
      if (to.fullPath === "/rooms/") {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: "잘못된 접근입니다.",
        })
        next({ name: "Office" })
      } else {
        next()
      }
    },
    children: [
      {
        path: "/rooms/:roomId",
        name: "Room",
        component: Room,
        props: true,
      },
    ],
  },
  {
    path: "/auth",
    component: AuthLayout,
    beforeEnter: (to, from, next) => {
      // 로그인 필수 X + 로그인 되어있다면 접근 금지
      if (!to.meta.loginRequired && localStorage.getItem("accessToken")) {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: "잘못된 접근입니다.",
        })
        next({ name: "Office" })
      } else {
        next()
      }
    },
    children: [
      {
        path: "reset-password",
        name: "ResetPassword",
        component: ResetPassword,
      },
      {
        path: "change-password",
        name: "ChangePassword",
        component: ChangePassword,
        meta: { loginRequired: true },
      },
    ],
  },
  {
    path: "/onboarding",
    component: AuthLayout,
    children: [
      {
        path: "signup",
        component: Signup,
      },
    ],
  },
  {
    // 위 경로를 제외한 모든 url 접근에 대해 NotFoundPage.vue를 rendering
    path: "/:pathMatch(.*)*",
    component: () => import("@/views/NotFoundPage.vue"),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

// to: 이동할 url에 해당하는 라우팅 객체
// from: 현재 url에 해당하는 라우팅 객체
// next: to에서 지정한 url로 이동하기 위해 꼭 호출해야 하는 함수
router.beforeEach((to, from, next) => {
  // 로그인하지 않은 유저 접근 금지인 페이지 -> 로그인으로 이동
  console.log(Boolean(localStorage.getItem("accessToken")))
  if (to.meta.loginRequired && !localStorage.getItem("accessToken")) {
    console.log("홈으로 이동")
    next({ name: "Home", params: { shouldLogin: true } })
    // 로그인한 유저 접근 금지인 페이지 -> 로비(?)로 이동
  } else if (!to.meta.loginRequired && localStorage.getItem("accessToken")) {
    next({ name: "Office" })
  } else next()
})

router.afterEach((to, from) => {
  if (to.fullPath.includes("office")) {
    if (from.fullPath === "/") {
      moveRoom(store.getters["auth/user"].roomId)
    } else {
      moveRoom(store.getters["office/lobbyId"])
    }
  } else if (to.fullPath.includes("rooms")) {
    console.log(to.params)
    moveRoom(to.params.roomId)
  }
})

export default router
