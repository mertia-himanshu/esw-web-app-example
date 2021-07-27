import type { RaRequest, RaResponse } from '../models/Models'
import { get, post } from './Http'

// #fetch-data
export const postRaValues = async (
  baseUrl: string,
  raRequest: RaRequest
): Promise<RaResponse | undefined> =>
  (await post<RaRequest, RaResponse>(baseUrl + 'raValues', raRequest))
    .parsedBody
// #fetch-data

// #secured-fetch-data
export const securedPostRaValues = async (
  baseUrl: string,
  raRequest: RaRequest,
  token: string
): Promise<RaResponse | undefined> =>
  (
    await post<RaRequest, RaResponse>(baseUrl + 'securedRaValues', raRequest, {
      Authorization: `Bearer ${token}`
    })
  ).parsedBody
// #secured-fetch-data

// #fetch-saved-ra-values
export const getRaValues = async (
  baseUrl: string
): Promise<RaResponse[] | undefined> =>
  (await get<RaResponse[]>(baseUrl + 'raValues')).parsedBody
// #fetch-saved-ra-values
