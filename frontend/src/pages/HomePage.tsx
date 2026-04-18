import { useEffect, useState } from "react"
import type { Earthquake } from "../types/earthquake"
import {deleteEarthquake, filterEarthquakes, getAllEarthquakes, refreshEarthquakes} from "../api/earthquakeApi"
import EarthquakeTable from "../components/EarthquakeTable";
import EarthquakeFilter from "../components/EarthquakeFilter";
import EarthquakeMap from "../components/EarthquakeMap.tsx";

export default function HomePage() {
    const [data, setData] = useState<Earthquake[]>([])
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(true)
    const [magnitude, setMagnitude] = useState("")
    const [time, setTime] = useState("")

    useEffect(() => {
        const loadData = async () => {
            try {
                setLoading(true)
                setError(null)
                await refreshEarthquakes()
                const result = await getAllEarthquakes()
                setData(result)
            } catch {
                setError("Failed to load earthquake data. Please try again.")
            } finally {
                setLoading(false)
            }
        }

        loadData()
    }, [])

    const handleFilter = async (magnitude?: number, time?: number) => {
        try {
            setLoading(true)
            setError(null)

            const result = await filterEarthquakes(magnitude, time)
            setData(result)
        } catch {
            setError("Failed to filter earthquakes.")
        } finally {
            setLoading(false)
        }
    }

    const handleReset = async () => {
        try {
            setLoading(true)
            setError(null)
            setMagnitude("")
            setTime("")

            const result = await getAllEarthquakes()
            setData(result)
        } catch {
            setError("Failed to load earthquakes.")
        } finally {
            setLoading(false)
        }
    }

    const handleDelete = async (id: number) => {
        try {
            await deleteEarthquake(id)
            setData(prev => prev.filter(e => e.id !== id))
        } catch {
            setError("Failed to delete earthquake.")
        }
    }

    return (
        <div className="page">
            <h1 className="title">Earthquakes Monitoring Application</h1>

            <div className="card">
                <EarthquakeFilter
                    magnitude={magnitude}
                    time={time}
                    setMagnitude={setMagnitude}
                    setTime={setTime}
                    onFilter={handleFilter}
                    onReset={handleReset}
                />
            </div>

            {loading && (
                <p>Loading earthquake data...</p>
            )}

            {error && (
                <p style={{ color: "red" }}>
                    {error}
                </p>
            )}

            {!loading && !error && data.length === 0 && (
                <p>No earthquake data available.</p>
            )}

            {!loading && !error && data.length > 0 && (
                <div>
                    <EarthquakeTable earthquakes={data} onDelete={handleDelete} />

                    <EarthquakeMap earthquakes={data} />
                </div>
            )}
        </div>
    )
}