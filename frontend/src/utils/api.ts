import type { RaRequest, RaResponse } from '../models/Models'
import { post } from './Http'

// #fetch-data
export const fetchRa = async (
  baseUrl: string,
  raRequest: RaRequest
): Promise<RaResponse | undefined> =>
  (await post<RaRequest, RaResponse>(baseUrl + 'formattedRa', raRequest))
    .parsedBody
// #fetch-data

// #secured-fetch-data
export const fetchSecuredRa = async (
  baseUrl: string,
  raRequest: RaRequest,
  token: string
): Promise<RaResponse | undefined> =>
  (
    await post<RaRequest, RaResponse>(
      baseUrl + 'securedFormattedRa',
      raRequest,
      {
        Authorization: `Bearer ${token}`
      }
    )
  ).parsedBody
// #secured-fetch-data
