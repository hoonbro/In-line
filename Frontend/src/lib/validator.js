export const requiredValidator = (formData, key) => {
  if (formData[key].value.length < 1) {
    formData[key].errors.required = "필수 입력 요소입니다."
    return false
  }
  delete formData[key].errors.required
  return true
}

export const loginRequiredValidator = (formData, key) => {
  if (formData[key].value.length < 1) {
    formData[key].errors.required =
      key === "email" ? "이메일을 입력해주세요." : "비밀번호를 입력해주세요."
    return false
  }
  delete formData[key].errors.required
  return true
}

export const emailValidator = (formData, key) => {
  if (
    !/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/.test(
      formData[key].value
    )
  ) {
    formData[key].errors.invalidEmail = "올바른 이메일 주소를 입력해주세요."
    return false
  }
  delete formData[key].errors.invalidEmail
  return true
}

export const confirmPasswordValidator = (formData, key) => {
  if (formData[key].value !== formData.password.value) {
    formData[key].errors.notMatch = "비밀번호가 일치하지 않습니다."
    return false
  }
  delete formData[key].errors.notMatch
  return true
}

export const passwordSecurityValidator = (formData, key) => {
  if (
    !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(
      formData[key].value
    )
  ) {
    formData[key].errors.weekPassword =
      "대소문자, 숫자, 특수문자 조합으로 8자리 이상으로 작성하세요."
    return false
  }
  delete formData[key].errors.weekPassword
  return true
}
