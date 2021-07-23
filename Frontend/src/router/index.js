import { createRouter, createWebHistory } from "vue-router"
import LandingLayout from "@/layouts/LandingLayout.vue"
import OfficeLayout from "@/layouts/OfficeLayout.vue"

import Home from "@/views/Home.vue"
import RegistOffice from "@/views/RegistOffice.vue"
import Login from "@/views/Login.vue"
import Office from "@/views/Office.vue"
import Members from "@/views/Members.vue"
import Admin from "@/views/Admin.vue"
import Room from "@/views/Room.vue"

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
      {
        path: "login",
        name: "Login2",
        component: Login,
      },
    ],
  },
  {
    path: "/office",
    component: OfficeLayout,
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
    path: "/rooms/:roomId",
    name: "Room",
    component: Room,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
