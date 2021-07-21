import { Button, Form, Input } from 'antd'
import React from 'react'
import { useLocationService } from '../../contexts/LocationServiceContext'
import { useAuth } from '../../hooks/useAuth'
import type { RaRequest } from '../../models/Models'
import { fetchSecuredRa } from '../../utils/api'
import { errorMessage } from '../../utils/message'
import { getBackendUrl } from '../../utils/resolveBackend'

export const SecuredRa = (): JSX.Element => {
  const locationService = useLocationService()
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
        const response = await fetchSecuredRa(backendUrl, valueInDecimal, token)
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
  // #add-component
}
