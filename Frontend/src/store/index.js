import { createStore } from "vuex"
import { landing } from "@/store/modules/landing"
import { office } from "@/store/modules/office"
import { room } from "@/store/modules/room"
import { onboard } from "@/store/modules/onboard"
import { auth } from "@/store/modules/auth"
import { socket } from "@/store/modules/socket"
import { admin } from "@/store/modules/admin"

export default createStore({
  modules: { landing, office, room, onboard, auth, socket, admin },
})
