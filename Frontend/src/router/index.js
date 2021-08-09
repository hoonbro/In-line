import { createRouter, createWebHistory } from "vue-router"
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

const routes = [
  {
    path: "/",
    component: LandingLayout,
    // localStorage에 jwt가 있으면 Office로 라우팅
    beforeEnter: (to, from, next) => {
      if (localStorage.getItem("accessToken")) next({ name: "Office" })
      else next()
    },
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
  // login이 필수인데, localStorage에 jwt가 없으면
  //  -> Home으로 보낸다 (로그인을 하라는 param과 함께)
  // const isLoginRequired = to.matched.some(
  //   routeInfo => routeInfo.meta.loginRequired
  // )
  if (to.meta.loginRequired && !localStorage.getItem("accessToken")) {
    next({ name: "Home", params: { shouldLogin: true } })
  } else next()
})

export default router
