import type { RaRequest, RaResponse } from '../models/Models'
import { post } from './Http'

// #fetch-data
export const fetchFormattedRa = async (
  baseUrl: string,
  raRequest: RaRequest
): Promise<RaResponse | undefined> =>
  (await post<RaRequest, RaResponse>(baseUrl + 'formattedRa', raRequest))
    .parsedBody
// #fetch-data
