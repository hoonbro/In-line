// 병훈
export const office = {
  namespaced: true,
  state: {
    user: {
      address: null,
      auth: "ROLE_ADMIN",
      deptId: 102,
      email: "asdf@asdf.asdf",
      jobId: 1008,
      joinDate: "2021-07-29T04:53:56.000+00:00",
      login: false,
      name: "김병훈",
      nickName: null,
      officeId: 16,
      password: "$2a$10$aZSj5sCJIZjUbsIy69AbauBPGuuDrvN2TGGoTmS4kSeNxloshm5Be",
      phone: "1231231234",
      profileImage: null,
      roomId: 1,
      userId: 5,
    },
  },
  mutations: {},
  getters: {
    getUser(state) {
      return state.user
    },
  },
  actions: {},
}
