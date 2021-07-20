import { Button, Form, Input } from 'antd'
import React from 'react'
import { useLocationService } from '../../contexts/LocationServiceContext'
import type { RaRequest } from '../../models/Models'
import { fetchFormattedRa } from '../../utils/api'
import { getBackendUrl } from '../../utils/resolveBackend'

export const Ra = (): JSX.Element => {
  const locationService = useLocationService()

  const onFinish = async (values: RaRequest) => {
    const backendUrl = await getBackendUrl(locationService)
    const valueInDecimal = { raInDecimals: Number(values.raInDecimals) }
    if (backendUrl) {
      const response = await fetchFormattedRa(backendUrl, valueInDecimal)
      console.log(response)
    }
  }

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
