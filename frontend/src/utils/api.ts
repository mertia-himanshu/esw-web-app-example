import type { RaRequest, RaResponse } from '../models/Models'
import { post } from './Http'

export const fetchFormattedRa = async (
  baseUrl: string,
  raRequest: RaRequest
): Promise<RaResponse | undefined> =>
  (await post<RaRequest, RaResponse>(baseUrl + 'formattedRa', raRequest))
    .parsedBody
