import { request } from './request'

export function getRequest (uri, params) {
  return request({
    url: uri,
    method: 'get',
    params: params
  })
}

export function postRequest (uri, params) {
  return request({
    url: uri,
    method: 'post',
    data: params
  })
}

export function deleteRequest (uri, params) {
  return request({
    url: uri,
    method: 'delete',
    params: params
  })
}

export function putRequest (uri, params) {
  return request({
    url: uri,
    method: 'put',
    data: params
  })
}
