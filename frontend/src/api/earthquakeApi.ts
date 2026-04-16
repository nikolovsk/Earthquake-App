import axios from "axios"
import type { Earthquake } from "../types/earthquake"

const BASE_URL = "http://localhost:8080/earthquakes"

export const getAllEarthquakes = async (): Promise<Earthquake[]> => {
    const res = await axios.get(BASE_URL)
    return res.data
}

export const filterEarthquakes = async (magnitude?: number, time?: number): Promise<Earthquake[]> => {
    const res = await axios.get(`${BASE_URL}/filter`, {
        params: { magnitude, time }
    })

    return res.data
}

export const refreshEarthquakes = async (): Promise<void> => {
    await axios.post(`${BASE_URL}/refresh`)
}

export const deleteEarthquake = async (id: number): Promise<void> => {
    await axios.delete(`${BASE_URL}/${id}`)
}