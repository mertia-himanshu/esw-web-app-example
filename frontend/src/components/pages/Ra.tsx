import { Button, Form, Input } from 'antd'
import React from 'react'
import { useLocationService } from '../../contexts/LocationServiceContext'
import type { RaRequest } from '../../models/Models'
import { fetchRa } from '../../utils/api'
import { getBackendUrl } from '../../utils/resolveBackend'

export const Ra = (): JSX.Element => {
  const locationService = useLocationService()

  // #use-fetch
  const onFinish = async (values: RaRequest) => {
    const backendUrl = await getBackendUrl(locationService)
    const valueInDecimal = { raInDecimals: Number(values.raInDecimals) }
    if (backendUrl) {
      const response = await fetchRa(backendUrl, valueInDecimal)
      if (response?.formattedRa) console.log(response.formattedRa)
      else {
        console.error(response)
        throw new Error('Invalid response, formattedRa field is missing')
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
