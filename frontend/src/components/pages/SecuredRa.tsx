import { Button, Form, Input } from 'antd'
import React from 'react'
import { useLocationService } from '../../contexts/LocationServiceContext'
import { useAuth } from '../../hooks/useAuth'
import type { RaRequest } from '../../models/Models'
import { securedPostRaValues } from '../../utils/api'
import { errorMessage } from '../../utils/message'
import { getBackendUrl } from '../../utils/resolveBackend'
// #add-component
// #use-location-service-from-context
export const SecuredRa = (): JSX.Element => {
  // #add-component
  const locationService = useLocationService()
  // #use-location-service-from-context
  const { auth } = useAuth()

  // #use-fetch
  const onFinish = async (values: RaRequest) => {
    const backendUrl = await getBackendUrl(locationService)
    const valueInDecimal = { raInDecimals: Number(values.raInDecimals) }

    if (backendUrl) {
      const token = auth?.token()
      if (!token) {
        errorMessage('Failed to greet user: Unauthenticated request')
      } else {
        const response = await securedPostRaValues(
          backendUrl,
          valueInDecimal,
          token
        )
        if (response?.formattedRa) console.log(response.formattedRa)
        else {
          console.error(response)
          throw new Error('Invalid response, formattedRa field is missing')
        }
      }
    }
  }
  // #use-fetch

  // #add-component
  return (
    <Form onFinish={onFinish}>
      <Form.Item label='RaInDecimals' name='raInDecimals'>
        <Input role='RaInDecimals' />
      </Form.Item>
      <Form.Item>
        <Button type='primary' htmlType='submit' role='Submit'>
          Submit
        </Button>
      </Form.Item>
    </Form>
  )
}
// #add-component
