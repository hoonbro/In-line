export const requiredValidator = (form, key, value) => {
  if (value.length < 1) {
    console.log("requiredValidator")
    return {
      key,
      type: "required",
      status: false,
      message: "필수 입력 요소입니다.",
    }
  }
  return { key, type: "required", status: true }
}

export const loginRequiredValidator = (form, key, value) => {
  if (value.length < 1) {
    return {
      key,
      type: "required",
      status: false,
      message:
        key === "email" ? "이메일을 입력해주세요." : "비밀번호를 입력해주세요.",
    }
  }
  return { key, type: "required", status: true }
}

export const emailValidator = (form, key, value) => {
  console.log(value)
  // https://www.w3resource.com/javascript/form/email-validation.php
  if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
    return {
      key,
      type: "invalidEmail",
      status: false,
      message: "올바른 이메일 주소를 입력해주세요.",
    }
  }
  return {
    key,
    type: "invalidEmail",
    status: true,
  }
}

export const confirmPasswordValidator = (form, key, value) => {
  console.log(form[key])
  if (value !== form.password.value) {
    return {
      key,
      type: "notMatch",
      status: false,
      message: "비밀번호가 일치하지 않습니다.",
    }
  }
  return {
    key,
    type: "notMatch",
    status: true,
  }
}

export const passwordSecurityValidator = (form, key, value) => {
  if (
    !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(
      value
    )
  ) {
    return {
      key,
      type: "weekPassword",
      status: false,
      message: "대소문자, 숫자, 특수문자 조합으로 8자리 이상으로 작성하세요.",
    }
  }
  return {
    key,
    type: "weekPassword",
    status: true,
  }
}

export const handleUpdateValidate = (formData, data) => {
  const { key, type, status, message } = data
  if (status) {
    delete formData[key].errors[type]
  } else {
    formData[key].errors[type] = message
  }
}
