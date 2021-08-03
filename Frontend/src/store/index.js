import { createStore } from "vuex"
import { landing } from "@/store/modules/landing"
import { office } from "@/store/modules/office"
import { room } from "@/store/modules/room"
import { auth } from "@/store/modules/auth"

export default createStore({
  modules: { landing, office, room, auth },
})
